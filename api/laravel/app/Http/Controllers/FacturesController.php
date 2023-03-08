<?php

namespace App\Http\Controllers;

use App\Models\Factura;

class FacturesController extends Controller
{
    public function index()
    {
        return response()->json(Factura::with(['lineafactura.marca', 'persona'])->get());
    }
}
