<?php

namespace App\Http\Controllers;

use App\Models\ArticlesSubcategories;
use Illuminate\Http\Request;

class ArticlesSubcategoriesControllers extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return response()->json(ArticlesSubcategories::all());
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
            if ($request->input("id_article") == "") {
                $articleSubcategory = new ArticlesSubcategories();
                $articleSubcategory->id_article = $request->id_article;
                $articleSubcategory->id_subcategory = $request->id_subcategory;
                $articleSubcategory->save();
                return response()->json($articleSubcategory);
            } else {
                $articleSubcategory = ArticlesSubcategories::find($request->input("id_article"));
                $articleSubcategory->id_article = $request->id_article;
                $articleSubcategory->id_subcategory = $request->id_subcategory;
                $articleSubcategory->save();
                return response()->json($articleSubcategory);
            }
        } catch (\Throwable $th) {
            return response()->json($th);
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
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
