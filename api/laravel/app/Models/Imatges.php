<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Imatges extends Model
{
    use HasFactory;

    protected $primaryKey = "id_imatge";
    public $timestamps = false;
    protected $table = 'imatges';

    public function propietats(){
        return $this->belongsToMany(Propietats::class, 'propietats_imatge', 'imatges_id_imatges', 'propietats_id_propietats');
    }


}
