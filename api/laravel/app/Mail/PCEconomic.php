<?php

namespace App\Mail;

use Illuminate\Bus\Queueable;
use Illuminate\Contracts\Queue\ShouldQueue;
use Illuminate\Mail\Mailable;
use Illuminate\Queue\SerializesModels;

class PCEconomic extends Mailable
{
    use Queueable, SerializesModels;

    public $nombre;
    public $stock;

    /**
     * Create a new message instance.
     *
     * @param $producto
     */
    public function __construct($nombre, $stock)
    {
        $this->nombre = $nombre;
        $this->stock = $stock;
    }

    /**
     * Build the message.
     *
     * @return $this
     */
    public function build()
    {
        return $this->subject('Stock actualizado del producto ' . $this->nombre)
            ->view('stock-actualizado')
            ->with(['producto' => $this->nombre, 'stock' => $this->stock]);
    }
}
