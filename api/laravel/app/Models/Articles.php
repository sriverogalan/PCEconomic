<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Articles extends Model
{
    use HasFactory;
    protected $primaryKey = "id_article";
    public $timestamps = false;
    protected $table = 'articles';

    public function marca()
    {
        return $this->belongsTo(Marques::class, 'id_marca');
    }

    public function subcategories()
    {
        return $this->belongsToMany(Subcategories::class, 'articles_subcategories', 'id_article', 'id_subcategoria');
    }

    public function propietats()
    {
        return $this->hasMany(Propietats::class, 'id_article');
    }
}
