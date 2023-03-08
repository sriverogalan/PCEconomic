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

            if ($request->input('es_principal') == 1) {
                $propietats = Propietats::where('id_article', $request->input('id_article'))->get();
                foreach ($propietats as $p) {
                    $p->es_principal = 0;
                    $p->save();
                }
            }

            $propietat->save();

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
        $propietat = Propietats::find($request->input('id_propietats'));
        $propietat->delete();
        return response()->json(['message' => 'Propietat eliminada correctamente'], 200);
    }
}
