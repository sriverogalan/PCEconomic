<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Articles;
use App\Models\Marques;

class ArticlesController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $article = Articles::with('marca')->get();
        return $article;
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create(Request $request)
    {
        try {
            if ($request->input("id_article") == "") {
                // Crear un nuevo artículo
                $article = new Articles;
                $article->nom = $request->input('nom');
                $article->descripcio = $request->input('descripcio');
                $article->pes = floatval($request->input('pes'));

                // Encontrar la marca por su nombre y asignar su ID al artículo
                $marca = Marques::where('nom', $request->input('marca'))->first();
                $article->id_marca = $marca->id_marca;

                $article->save();
                return response()->json(['message' => 'Article creat correctament'], 201);
            } else {
                // Actualizar un artículo existente
                $article = Articles::find(intval($request->input('id_article')));
                if (!$article) {
                    return response()->json(['message' => 'Article no trobat'], 404);
                }
                // Actualizar los campos del artículo
                $article->nom = $request->input('nom');
                $article->descripcio = $request->input('descripcio');
                $article->pes = floatval($request->input('pes'));

                // Encontrar la marca por su nombre y asignar su ID al artículo
                $marca = Marques::where('nom', $request->input('marca'))->first();
                $article->id_marca = $marca->id_marca;

                $article->save();
                return response()->json(['message' => 'Article actualitzat correctament'], 200);
            }
        } catch (\Exception $e) {
            return response()->json(['message' => $e], 500);
        }
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store()
    {
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
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
            $article = Articles::find(intval($request->input('id_article')));
            if (!$article) {
                return response()->json(['message' => 'Article no trobat'], 404);
            }
            $article->delete();
            return response()->json(['message' => 'Article eliminat correctament'], 200);
        } catch (\Exception $e) {
            if ($e->errorInfo[1] === 1451) {
                // If the error code is 1451, a foreign key constraint violation occurred
                return response()->json(['message' => 'No es pot eliminar l\'article perquè té propietats o una categoria asignada']);
            } else {
                // Handle other types of database errors
                return response()->json(['message' => 'Error al eliminar l\'article'], 500);
            }
        }
    }
}
