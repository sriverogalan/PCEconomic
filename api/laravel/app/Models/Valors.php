<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Valors extends Model
{
    use HasFactory;

    protected $primaryKey = "id_valor";
    public $timestamps = false;
    protected $table = 'valors';

    public function propietat()
    {
        return $this->belongsToMany(Propietat::class, 'valors_propietat', 'valor_id_valor', 'propietat_id_propietat');
    }
}
