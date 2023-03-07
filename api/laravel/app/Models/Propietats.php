<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Propietats extends Model
{
    use HasFactory;

    protected $primaryKey = "id_propietats";
    public $timestamps = false;
    protected $table = 'propietats';

    public function article()
    {
        return $this->belongsTo(Articles::class, 'id_article');
    }

    public function valors()
    {
        return $this->belongsToMany(Valors::class, 'propietats_valor', 'propietats_id_propietats', 'valor_id_valor');
    }

    public function imatges(){
        return $this->belongsToMany(Imatges::class, 'propietats_imatges', 'propietats_id_propietats', 'imatges_id_imatge');
    }
}
