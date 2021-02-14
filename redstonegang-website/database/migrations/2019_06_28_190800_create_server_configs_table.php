<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateServerConfigsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('server_configs', function (Blueprint $table) {
            $table->string('name')->primary();
            $table->string('value');
            $table->timestamps();
        });
        $defaults = \App\ServerConfig::getDefaults();
        foreach ($defaults as $key => $value) {
            \App\ServerConfig::create(['name' => $key, 'value' => $value]);
        }
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('server_configs');
    }
}
