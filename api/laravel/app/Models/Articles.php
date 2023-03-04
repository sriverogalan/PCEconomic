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
}
