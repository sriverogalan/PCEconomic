<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Rols extends Model
{
    use HasFactory;

    protected $table = 'rols';

    protected $primaryKey = 'id_rol';

    public function persones()
    {
        return $this->belongsToMany(Persones::class, 'persones_rols', 'id_rol', 'id_persona');
    }
}
