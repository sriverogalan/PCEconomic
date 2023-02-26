<?php

namespace App\Http\Controllers;

use App\Models\Categories;
use App\Models\Subcategories;
use Illuminate\Http\Request;

class CategoriaController extends Controller
{

    public function getCategories()
    {
        return response()->json(Categories::all());
    }

    public function getSubcategories()
    {
        return response()->json(Subcategories::all());
    }

    public function getSubcategoriesByCategoria($id)
    {
        return response()->json(Categories::find($id)->subcategories);
    }

    public function createCategoria(Request $request)
    {
        $categoria = new Categories();
        $categoria->nom = $request->nom;
        $categoria->save();
        return response()->json($categoria);
    }

    public function createSubcategoria(Request $request)
    {
        $subcategoria = new Subcategories();
        $subcategoria->nom = $request->nom;
        $subcategoria->categoria_id = $request->categoria_id;
        $subcategoria->save();
        return response()->json($subcategoria);
    }

    public function updateCategoria(Request $request)
    {
        $categoria = Categories::find($request->id);
        $categoria->nom = $request->nom;
        $categoria->save();
        return response()->json($categoria);
    }

    public function updateSubcategoria(Request $request)
    {
        $subcategoria = Subcategories::find($request->id);
        $subcategoria->nom = $request->nom;
        $subcategoria->categoria_id = $request->categoria_id;
        $subcategoria->save();
        return response()->json($subcategoria);
    }

    public function deleteCategoria(Request $request)
    {
        $categoria = Categories::find($request->id);
        $categoria->delete();
        return response()->json($categoria);
    }

    public function deleteSubcategoria(Request $request)
    {
        $subcategoria = Subcategories::find($request->id);
        $subcategoria->delete();
        return response()->json($subcategoria);
    }
}
