<?php

use Illuminate\Support\Facades\Route;
use App\Models\Persones;
use App\Models\Clients;
use App\Models\Admins;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});

Route::get('/persones', function () {
    $persones = Persones::all();
    return $persones->toJson();
});

Route::get('/persones/{id}', function ($id) {
    $persones = Persones::find($id);
    return $persones->toJson();
});

Route::get('/clients', function () {
    $clientes = Clients::with('persones')->get();
    return $clientes->toJson();
});

Route::get('/admins', function () {
    $admins = Admins::all();
    return $admins->toJson();
});
