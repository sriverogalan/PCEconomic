<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Persones extends Model
{
    use HasFactory;

    protected $table = 'persones';

    protected $primaryKey = 'id_persona';

    protected $attributes = [
        'nom' => '',
        'cognom1' => '',
        'cognom2' => '',
        'email' => '',
        'telefon' => '',
        'contraseña' => '',
    ];

    public function rols()
    {
        return $this->belongsToMany(Rols::class, 'persones_rols', 'id_persona', 'id_rol');
    }
}
