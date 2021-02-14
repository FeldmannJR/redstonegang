<?php

namespace App\Jobs;

use App\CustomSkin;
use App\Services\Vendor\MojangAccountService;
use App\Skin;
use Illuminate\Bus\Queueable;
use Illuminate\Queue\SerializesModels;
use Illuminate\Queue\InteractsWithQueue;
use Illuminate\Contracts\Queue\ShouldQueue;
use Illuminate\Foundation\Bus\Dispatchable;


class UploadSkin implements ShouldQueue
{
    use Dispatchable, InteractsWithQueue, Queueable, SerializesModels;

    /**
     * Usuario da conta que vai ser feito o upload
     */
    private $username;
    private $skinPath;
    private $skinName;
    private $model;
    private $tries = 10;

    /**
     * Create a new job instance.
     *
     * @param $username
     * @param $skinPath
     * @param $skinName
     * @param $model
     */
    public function __construct($username, $skinPath, $skinName, $model = false)
    {
        //
        $this->username = $username;
        $this->skinPath = $skinPath;
        $this->skinName = $skinName;
        $this->model = $model;
    }

    /**
     * Execute the job.
     *
     * @return void
     * @throws \Exception
     */
    public function handle()
    {
        $accounts = config("redstonegang.minecraft.accounts");
        if (!array_key_exists($this->username, $accounts)) {
            // Se não tem mais a conta que foi bindada pra dar upload seta outra
            UploadSkin::dispatch(array_rand($accounts), $this->skinPath, $this->username, $this->model);
            return;
        }
        // Bota um lock pra só poder dar upload a cada 60 segundos
        \Redis::throttle('upload-skin-' . $this->username)->allow(1)->every(60)->then(function () {
            // Deixa só um worker executar por conta
            \Redis::funnel('upload-concurrency-skin-' . $this->username)->limit(1)->then(function () {
                $this->upload();
            }, function () {
                $this->release(10);
            });
        }, function () {
            $this->release(10);
        });
    }

    private function upload()
    {
        $mojang = app()->make(MojangAccountService::class);

        // Cacheia por 10 minutos o token
        $token = \Cache::remember('mojang-access-token:' . $this->username, 60 * 10, function () use ($mojang) {
            $accounts = config("redstonegang.minecraft.accounts");
            return $mojang->authenticate($this->username, $accounts[$this->username]);
        });

        if ($token === null) {
            throw new \Exception('Não consegui puxar o token!');
        }
        $accessToken = $token->accessToken;
        $uuid = $token->selectedProfile->id;
        $name = $token->selectedProfile->name;
        $response = $mojang->uploadSkin($accessToken, $uuid, \Storage::get($this->skinPath), $this->model ? 'slim' : '');
        if ($response === true) {
            $skin = $mojang->getProfileSkin($uuid, false);
            if ($skin !== null) {
                // Deu upload com sucesso e puxou assinatura
                $signature = null;
                $textures = null;
                foreach ($skin->properties as $value) {
                    if ($value->name == 'textures') {
                        $textures = $value->value;
                        $signature = $value->signature;
                    }
                }
                if ($signature !== null) {
                    $skinmodel = Skin::create(['signature' => $signature, 'textures' => $textures, 'uuid' => $uuid, 'name' => $name]);
                    if ($this->skinName !== null) {
                        CustomSkin::updateOrCreate(
                            ['name' => $this->skinName],
                            ['skin_id' => $skinmodel->id]);
                    }
                    \Storage::delete($this->skinPath);
                    return;
                }
            } else {
                throw new \Exception('Skin venho nula!');
            }
        } else {
            throw new \Exception('Não deu upload na skin!', 0, $response);
        }

    }

}
