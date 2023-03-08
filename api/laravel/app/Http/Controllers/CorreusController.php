<?php

namespace App\Http\Controllers;

use App\Models\CorreuNoStock;

class CorreusController extends Controller
{
    public function index()
    {
        return response()->json(CorreuNoStock::with('propietats')->get());
    }
}
