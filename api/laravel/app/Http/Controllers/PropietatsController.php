<?php

namespace App\Http\Controllers;

use App\Models\Propietats;
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
        )->with('valors.propietat')->get();

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

            if ($request->id_article == "") {
                $propietat = new Propietats;
                $propietat->id_article = $request->input('id_article');
                $propietat->nom = $request->input('nom');
                $propietat->valor = $request->input('valor');
                $propietat->save();
            } else {
                $propietat = Propietats::find($request->input('id_propietats'));
                $propietat->nom = $request->input('nom');
                $propietat->valor = $request->input('valor');
                $propietat->save();
            }
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
        $propietat = Propietats::find($request->input('id_propietats'));
        $propietat->delete();
        return response()->json(['message' => 'Propietat eliminada correctamente'], 200);
    }
}
