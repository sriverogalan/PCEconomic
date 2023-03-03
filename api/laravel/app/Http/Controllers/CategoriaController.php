<?php

namespace App\Http\Controllers;

use App\Models\Categories;
use App\Models\Subcategories;
use Illuminate\Http\Request;

class CategoriaController extends Controller
{

    public function index()
    {
        $categories = Categories::all();
        return response()->json($categories);
    }

    public function store(Request $request)
    {
        $category = new Categories;
        $category->nom = $request->input('nom');
        $category->is_active = 1;
        $category->save();
        return response()->json(['message' => 'Categoria creada correctamente'], 201);
    }

    public function update(Request $request)
    {
        $categories = Categories::find($request->input('id_categoria'));
        $categories->nom = $request->input('nom');
        $categories->is_active = 1;
        $categories->save();
        return response()->json(['message' => 'Categoria editada correctamente'], 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy(Request $request)
    {
        $categories = Categories::find($request->input('id_categoria'));
        $categories->is_active = 0;
        $categories->save();
        return response()->json(['message' => 'Categoria eliminada correctamente'], 200);
    }
}
