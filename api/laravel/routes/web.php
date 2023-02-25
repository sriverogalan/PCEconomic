<?php

use App\Http\Controllers\JwtController;
use Illuminate\Support\Facades\Route;
use App\Models\Persones;
use App\Http\Controllers\MarquesController;

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
    return response()->json(true);
});

Route::controller(MarquesController::class)->group(function () {
    Route::get('/api/get/marques', 'index');
    Route::post('/api/create/marques', 'create');
    Route::post('/api/update/marques', 'update');
    Route::post('/api/delete/marques', 'delete');
});

Route::get('/persones', function () {
    $persones = Persones::all();
    return response()->json($persones);
});

Route::get('/persones/{id}', function ($id) {
    $persones = Persones::find($id);
    return response()->json($persones);
});

Route::controller(CategoriaController::class)->group(function () {
    Route::get('/api/get/categoria', 'index');
    Route::post('/api/create/categoria', 'create');
    Route::post('/api/update/categoria', 'update');
    Route::post('/api/delete/categoria', 'delete');
});