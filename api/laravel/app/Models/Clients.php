<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Clients extends Model
{
    use HasFactory;

    protected $table = 'clients';

    protected $primaryKey = 'id_client';

    protected $attributes = [
        'dni' => '',
        'isActive' => '',
        'isSubscribed' => '',
        'id_carrito' => '', // FK
    ];

    public function persones()
    {
        return $this->belongsTo(Persones::class, 'id_persona', 'id_persona');
    }
}