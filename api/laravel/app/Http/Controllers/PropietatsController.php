<?php

namespace App\Http\Controllers;

use App\Models\Propietat;
use App\Models\Propietats;
use App\Models\Valors;
use Exception;
use Illuminate\Http\Request;

class PropietatsController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index(Request $request)
    {

        $vals = Propietats::where(
            'id_article',
            $request->input('id_article')
        )
            ->with('valors.propietat')
            ->with('imatges')
            ->get();


        return response()->json($vals);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function create(Request $request)
    {
        try {
            if ($request->input('id_propietats') == null) {
                $propietat = new Propietats();
            } else {
                $propietat = Propietats::find($request->input('id_propietats'));
            }


            $propietat->es_principal = $request->input('es_principal');
            $propietat->preu = $request->input('preu');
            $propietat->stock = $request->input('stock');
            $propietat->id_article = $request->input('id_article');

            if (
                $request->input("preu") == "" || $request->input('stock') == ""
            ) {
                return response()->json(['message' =>
                'El preu i el stock no poden estar buits'], 400);
            }
            $var = array_keys($request->input('propietats_valors'));
            $props_valors = $request->input('propietats_valors');

            $propietat->save();

            $array = [];

            foreach ($var as $prop) { 
                $propBD = Propietat::where('nom', $prop)->first();
                if (!$propBD) {
                    $propBD = new Propietat();
                    $propBD->nom = $prop;
                    $propBD->save();
                }
                foreach ($props_valors[$prop] as $valors) {
                    $valor = Valors::where('valor', $valors)->first();
                    if (!$valor) {
                        $valor = new Valors();
                        $valor->valor = $valors;
                        $valor->save();
                    }
                    $valor->propietat()->attach($propBD);
                }
                $propietat->valors()->attach($valor);
            }

            $message = ($request->input('id_propietats') == null)
                ? 'Propietat creada correctamente'
                : 'Propietat actualizada correctamente';

            return response()->json(['message' => $message], 200);
        } catch (Exception $e) {
            return response()->json(['message' => $e], 500);
        }
    }


    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function delete(Request $request)
    {
        $propietat = Propietats::find($request->input('id_propietat'));
        $propietat->delete();
        return response()->json(['message' => 'Propietat eliminada correctamente'], 200);
    }
}
