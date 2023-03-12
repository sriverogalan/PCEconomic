<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Valoracions extends Model
{
    use HasFactory;

    protected $table = 'valoracions';

    protected $primaryKey = 'id_valoracio';
}
