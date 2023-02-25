<?php

namespace App\Http\Controllers;

use App\Models\Categories;
use Illuminate\Http\Request;

class CategoriaController extends Controller
{
    
    public function index() {
        return response()->json(Categories::all());
    }

    

}
