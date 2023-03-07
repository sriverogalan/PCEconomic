<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class LineasFactura extends Model
{
    use HasFactory;

    protected $table = 'lineas_factura';
    protected $primaryKey = 'id_linea_factura';

    public function factura()
    {
        return $this->belongsToMany(Factura::class, 'factura_lineas_facturas', 'lineas_facturas_id_linea_factura', 'factura_id_factura');
    }

    public function marca()
    {
        return $this->belongsTo(Marques::class, 'id_marca');
    }
}
