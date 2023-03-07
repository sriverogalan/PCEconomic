<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Propietat extends Model
{
    use HasFactory;

    protected $primaryKey = "id_propietat";
    public $timestamps = false;
    protected $table = 'propietat';
}
