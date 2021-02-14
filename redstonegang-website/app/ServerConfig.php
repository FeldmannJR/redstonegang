<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class ServerConfig extends Model
{

    /*String database_host;
    String database_schema;
    String database_user;
    String database_password;

    String network_host;
    String network_port;
    String network_token;
     * */

    protected $primaryKey = 'name';
    public $incrementing = false;

    protected $guarded = [];

    public static function getDefaults()
    {
        $arr = [
            'database_host' => "database",
            'database_schema' => "redstonegang_common",
            'database_user' => "",
            'database_password' => "",
            //
            'network_host' => "network",
            'network_port' => "9000",
            'network_token' => "t8h6fr5ghaa7r567A",
        ];
        if (\App::environment("local")) {
            $arr['database_user'] = config("database.connections.mysql.username");
            $arr['database_password'] = config("database.connections.mysql.password");
        }
        return $arr;


    }
}
