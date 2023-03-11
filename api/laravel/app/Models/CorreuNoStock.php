<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class CorreuNoStock extends Model
{
    use HasFactory;
    protected $table = 'correo_no_stock';
    protected $primaryKey = 'id_correo';

    public function propietats()
    {
        return $this->belongsTo(Propietats::class, 'id_propietats');
    }
}
