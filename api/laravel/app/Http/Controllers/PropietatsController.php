<?php

namespace App\Http\Controllers;

use App\Models\Propietat;
use App\Mail\PCEconomic;
use App\Models\CorreuNoStock;
use App\Models\Propietats;
use App\Models\Valors;
use Exception;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Storage;
use Illuminate\Support\Facades\Mail;

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
            ->with(['valors.propietat', 'imatges'])
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

            // si el stock es mayor a 0 enviamos correo
            if ($propietat->stock > 0) {
                $correos = CorreuNoStock::where('id_propietats', $request->input('id_propietats'))->get();
                foreach ($correos as $c) {
                    $email = $c->email;
                    $correo = new PCEconomic();
                    Mail::to($email)->send($correo);
                    $c->delete();
                }
            }

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

            foreach ($var as $prop) {
                $propBD = Propietat::where('nom', $prop)->first();
                if ($propBD == null) {
                    $propBD = new Propietat();
                    $propBD->nom = $prop;
                    $propBD->save();
                } 
                $valor = Valors::where('valor', $props_valors[$prop])->first();
                if ($valor == null) {
                    $valor = new Valors();
                    $valor->valor = $props_valors[$prop];
                    $valor->save();
                    $valor->propietat()->detach();
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
        try {
            $propietat = Propietats::find($request->input('id_propietat'));
            $propietat->valors()->detach();
            $propietat->imatges()->delete();
            $propietat->delete();

            return response()->json(['message' => 'Propietat eliminada correctamente'], 200);
        } catch (Exception $e) {
            return response()->json(['message' => $e], 500);
        }
    }
}
