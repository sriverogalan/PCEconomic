<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class CorreuNoStock extends Model
{
    use HasFactory;

    protected $table = 'correos_usuarios_no_stock';
    protected $primaryKey = 'id_correo';

    public function propietats()
    {
        return $this->hasOne(Propietats::class, 'id_propietats', 'id_propietats');
    }
}
