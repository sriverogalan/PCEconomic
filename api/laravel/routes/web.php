<?php

use App\Http\Controllers\ArticlesController;
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
    // TODO: Hacer papelera para recuperar eliminados
});

Route::controller(CategoriaController::class)->group(function () {
    Route::get('/api/get/categoria', 'getCategories');
    Route::get('/api/get/subcategories', 'getSubcategories');
    Route::get('/api/get/subcategories/{id}', 'getSubcategoriesByCategoria');
    Route::post('/api/create/categoria', 'createCategoria');
    Route::post('/api/create/subcategoria', 'createSubcategoria');
    Route::post('/api/update/categoria', 'updateCategoria');
    Route::post('/api/update/subcategoria', 'updateSubcategoria');
    Route::post('/api/delete/categoria', 'deleteCategoria');
    Route::post('/api/delete/subcategoria', 'deleteSubcategoria');
    
 
Route::controller(ArticlesController::class)->group(function () {
    Route::get('/api/get/articles', 'index');
    Route::post('/api/create/articles', 'create');
    Route::post('/api/update/articles', 'update');
    Route::post('/api/delete/articles', 'delete');
});

Route::controller(PersonesController::class)->group(function () {
    Route::post('/api/create/persones', 'create');
    Route::post('/api/update/persones', 'update');
    Route::post('/api/delete/persones', 'delete');