<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Persones extends Model
{
    use HasFactory;

    protected $table = 'persones';
    protected $primaryKey = 'id_persona';

    public function rols()
    {
        return $this->belongsToMany(Rols::class, 'persones_rols', 'id_persona', 'id_rol');
    }
}
