<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Marques;

class MarquesController extends Controller
{


    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return response()->json(Marques::all());
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create(Request $request)
    {
        $marca = new Marques;
        $marca->nom = $request->input('nom');
        $marca->cif = $request->input('cif');
        $marca->is_actiu = true;

        $marca->save();

        return response()->json(['message' => 'Marca creada correctamente'], 201);
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

        $marca = Marques::find($request->input('id_marca'));
        $marca->nom = $request->input('nom');
        $marca->cif = $request->input('cif');
        $marca->is_actiu = true;
        $marca->save();
        return response()->json(['message' => 'Marca actualizada correctamente'], 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function delete(Request $request)
    {
        $marca = Marques::find($request->input('id_marca'));
        $marca->is_actiu = 0;
        $marca->save();
        return response()->json(['message' => 'Marca eliminada correctamente'], 200);
    }
}
