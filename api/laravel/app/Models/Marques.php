<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Marques extends Model
{
    use HasFactory; 
    public $timestamps = false;
    protected $table = 'marques';
}
