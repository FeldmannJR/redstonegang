<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateCustomSkinsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('custom_skins', function (Blueprint $table) {
            $table->string('name', 13)->primary();
            $table->bigInteger('skin_id')->unsigned();
            $table->timestamps();
            $table->foreign('skin_id')->references('id')->on('redstonegang_app.skins');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('custom_skins');
    }
}
