<?php

namespace App\Http\Controllers;

use App\Models\Persones;
use App\Models\Rols;
use Illuminate\Http\Request;

class PersonesController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return response()->json(Persones::with('rols')->get());
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
    public function destroy(Request $request)
    {
        try {
            $person = Persones::find($request->input('id_persona'));
            if (!$person) {
                return response()->json(['message' => 'Person not found'], 404);
            }
            $person->rols()->detach();
            $person->delete();
            return response()->json(['message' => 'Person deleted'], 200);
        } catch (\Exception $e) {
            return response()->json(['message' => $e], 500);
        }
    }

    public function addPersonAsAdmin(Request $request)
    {
        try {
            $person = Persones::find($request->input('id_persona'));
            if (!$person) {
                return response()->json(['message' => 'Person not found'], 404);
            }
            $rol = Rols::where('name', 'ADMINISTRADOR')->first();

            if (!$rol) return response()->json(['message' => 'Rol not found'], 404);

            $person->rols()->attach($rol->id_rol);
            $person->save();
            return response()->json(['message' => 'Person added as admin'], 200);
        } catch (\Exception $e) {
            return response()->json(['message' => $e], 500);
        }
    }
}
