<?php

namespace App\Http\Controllers;

use App\Models\Persones;
use Illuminate\Http\Request;
use \Firebase\JWT\JWT;


class JwtController extends Controller
{

    public function generateToken(Request $request)
    {

        $expiration_time = time() + (7 * 24 * 60 * 60);
        $persona = Persones::where('email', 'hulkrojo2@gmail.com')->first();

        /*if (!$persona) {
            return response()->json(['error' => 'Email no existeix'], 404);
        } */
        $payload = [
            'email' => 'hulkrojo2@gmail.com',
            'rols' => $persona->rols->pluck('name'),
            'iat' => time(),
            'exp' => $expiration_time
        ];

        $jwt = JWT::encode($payload, env('JWT_SECRET'), 'HS256');

        return response()->json($jwt);
    }
}
