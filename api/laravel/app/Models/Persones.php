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

    public function clients()
    {
        return $this->hasOne(Clients::class, 'id_persona', 'id_persona');
    }

    public function admins()
    {
        return $this->hasOne(Admins::class, 'id_persona', 'id_persona');
    }
}
