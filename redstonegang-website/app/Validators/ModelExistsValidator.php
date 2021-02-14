<?php


namespace App\Validators;


use Illuminate\Database\Eloquent\Model;
use Illuminate\Validation\Concerns\ValidatesAttributes;
use Illuminate\Validation\Rule;

class ModelExistsValidator
{

    public function validate($attribute, $value, $parameters, $validator)
    {
        if (count($parameters) == 0) {
            throw new \Exception('Modelo não recebido!');
        }
        $class = $parameters[0];
        if (!class_exists($class)) {
            throw new \Exception('Classe do model não encontrada!');
        }
        /** @var Model $instance */
        $instance = new $class;
        if (!$instance instanceof Model) {
            throw new \Exception('Classe fornecida não é um modelo!');
        }

        $connection = $instance->getConnectionName();
        $table = $instance->getTable();
        $column = $instance->getKeyName();

        if ($connection === null) {
            return \DB::table($table)->where($column, $value)->exists();
        } else {
            return \DB::connection($connection)->table($table)->where($column, $value)->exists();
        }

    }
}