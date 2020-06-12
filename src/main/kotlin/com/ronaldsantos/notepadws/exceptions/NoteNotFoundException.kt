package com.ronaldsantos.notepadws.exceptions

import java.lang.RuntimeException

class NoteNotFoundException(id: Long) : RuntimeException("Could not find note $id")