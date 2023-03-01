<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Subcategories extends Model
{
    use HasFactory;

    protected $table = 'subcategories';
    protected $primaryKey = 'id_subcategoria';
    public $timestamps = false;

    public function categories()
    {
        return $this->belongsTo(Categories::class, 'id_categoria');
    }
}
