<?php

namespace App\Http\Controllers;

use App\Models\Categories;
use App\Models\Subcategories;
use Illuminate\Http\Request;

class SubcategoriaController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */

    public function index()
    {
        $subcategories = Subcategories::with("categories")->get();;
        return response()->json($subcategories);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $subcategory = new Subcategories;
        $subcategory->nom = $request->input('nom');
        $subcategory->is_active = 1;
        $subcategory->id_categoria = $request->input('id_categoria');
        $subcategory->save();
        return response()->json(['message' => 'Subcategoria creada correctamente'], 201);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request)
    {
        $subcategory = Subcategories::find($request->input('id_subcategoria'));
        $subcategory->nom = $request->input('nom');
        $subcategory->is_active = 1;
        $subcategory->id_categoria = $request->input('id_categoria');
        $subcategory->save();
        //['message' => 'Subcategoria actualizada correctamente']
        return response()->json($subcategory, 201);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy(Request $request)
    {
        $subcategory = Subcategories::find($request->input('id_subcategoria'));
        $subcategory->is_active = 0;
        $subcategory->save();
        return response()->json(['message' => 'Subcategoria eliminada correctamente'], 201);
    }
}
