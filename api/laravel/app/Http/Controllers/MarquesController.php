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
        // Crear una nueva instancia del modelo Marca y asignar los valores recibidos
        $marca = new Marques;
        $marca->nom = $request->input('nom');
        $marca->cif = $request->input('cif');
        $marca->is_actiu = true;

        // Guardar la nueva marca en la base de datos
        $marca->save();

        // Devolver una respuesta al cliente
        return response()->json(['message' => 'Marca creada correctamente'], 201);
    }
    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        //
    }
}
