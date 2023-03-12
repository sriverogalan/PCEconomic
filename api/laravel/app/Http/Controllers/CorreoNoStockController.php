<?php

namespace App\Http\Controllers;

use App\Mail\PCEconomic;
use App\Models\CorreuNoStock;
use App\Models\Propietats;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Mail;

class CorreoNoStockController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return response()->json(CorreuNoStock::all());
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function sendEmail(Request $request)
    {
        // encuentra CorreuNoStock por email
        $c = CorreuNoStock::where('email', $request->input('email'))->first();

        $propietat = Propietats::find($request->input('id_propietats'));
        $email = $request->input('email');

        $correo = new PCEconomic($propietat->article->nom, $propietat->stock);
        Mail::to($email)->send($correo);
        $c->delete();
        return response()->json(['message' => 'Email sent'], 200);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        //
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
    public function destroy($id)
    {
        //
    }
}
