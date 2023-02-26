<?php

namespace App\Http\Controllers;

use App\Models\Persones;
use Illuminate\Http\Request;

class PersonesController extends Controller
{
    public function index()
    {
        return response()->json(Persones::all());
    }

    public function show($id)
    {
        return response()->json(Persones::find($id));
    }
}
