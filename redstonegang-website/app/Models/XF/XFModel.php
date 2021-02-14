<?php


namespace App\Models\XF;


use Illuminate\Database\Eloquent\Model;

class XFModel extends Model
{
    protected $connection = "mysql_xenforo";
    public $timestamps = false;
}