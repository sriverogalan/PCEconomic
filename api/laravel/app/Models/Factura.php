<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Factura extends Model
{
    use HasFactory;

    protected $table = 'factura';
    protected $primaryKey = 'id_factura';

    public function lineafactura()
    {
        return $this->belongsToMany(LineasFactura::class, 'factura_lineas_facturas', 'factura_id_factura', 'lineas_facturas_id_linea_factura');
    }

    public function persona()
    {
        return $this->belongsTo(Persones::class, 'client_id_persona');
    }
}
