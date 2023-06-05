import java.util.Date

fun main(args: Array<String>) {
    println("Hello World!")

    // Tipos de variables

    //INMUTABLES (No se reasignan "=")
    val inmutable: String = "Denis";
    //inmutable = "David";

    //Mutables (Re asignar)
    var mutable: String = "Denis";
    mutable = "David";

    // val > var
    //Se prefiere las inmutables antes que las mutables

    //Duck Typing.
    val ejemploVariable = "Denis Araque"
    val edadEjemplo : Int = 12
    ejemploVariable.trim()

    // variable primitiva
    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true
    //Clases Java
    val fechaNacimiento: Date = Date()

    //SWITCH
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C")->{
            println("Casado")
        }
        "S" ->{
            println("Soltero")
        }
        else ->{
            println("No sabemos")
        }
    }
    val coqueteo = if (estadoCivilWhen=="S") "Si" else "No"
    calcularSueldo(10.00)
    calcularSueldo(10.00,12.00,20.00)
    calcularSueldo(10.00, bonoEspecial = 20.00) //Named Parameters, parametros nombrados
    calcularSueldo(bonoEspecial = 12.00, sueldo = 10.00, tasa = 14.00)// Parametros nombrados

    // se inician diferentes instancias
    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null,1)
    val sumaTres = Suma(1,null)
    val sumaCuatro = Suma(null,null)
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    ARREGLOS
    //Arreglos estaticos, no se puede modificar el contenido
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico)

    //Arreglo Dinamico
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int> (1,2,3,4,5,6,7,8,9,10)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)


    // operaciones
    // estan dentro de un arreglo estatico o dinamico
    //recibe un parametro dentro de esta funcion
    val respuestaForEach: Unit = arregloDinamico
        .forEach { valorActual: Int ->
            println("Valor actual: ${valorActual}")
        }

    //si no se quiere escribir mucho codigo y escribir funciones que usen un solo parametro se puede usar it

    arregloDinamico.forEach { println(it)}
    //recibe dos parametros dentro de esta funcion
    //For each indexed, aparte de mandar el valor actual manda el indice

    arregloEstatico
        .forEachIndexed { indice: Int, valorActual: Int ->
            println("Valor ${valorActual} Indice: ${indice}")
        }
    println(respuestaForEach)


    //MAP muta o cmbia el arreglo
    // envia el nuevo valor de la iteracion
    //Nos devuelve un nuevo arreglo con los valores modificados

    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }
    println(respuestaMap)
    val respuestaMapDos= arregloDinamico.map {it+15}

    //FILTER-> filtrar el arreglo
    //devolver una expresion (true or false)
    //nuevo arreglo filtrado
    val respuestaFilter: List<Int> =arregloDinamico
        .filter{ valorActual:Int ->
            val mayoresACinco: Boolean = valorActual > 5 //expresion condicion
            return@filter mayoresACinco

        }
    val respuestaFilterDos = arregloDinamico.filter {it<=5}
    println(respuestaFilter)
    println(respuestaFilterDos)

    //OR AND
    //OR ->ANY (Algunos cumple?)
    //AND -> ALL (Todos cumplen?)
    val respuestaAny : Boolean = arregloDinamico
        .any{
            valorActual: Int->
            retun@any (valorActual > 5)
        }
    println(respuestaAny)//true

    val respuestaAll: Boolean = arregloDinamico
        .all{ valorActual: Int ->
            return@all (valorActual > 5)
        }
    println(respuestaAll) //FALSE

    //REDUCE
    //recibe dos parametros, es primero es el valor acumulado y siempre empiza en cero y aparte el arreglo
    //valor acumulado (0)
    //[1,2,3,4,5]->sumar todos los valores del arreglo
    //valor Iteacion1= valor empieza+1=0+1=1->Iteacion1
    //valor Iteacion2= valor Iteacion1+2=2+1=3->Iteacion2
    //y asi sucesivamente hasta que se sumen todos los valores del arreglo
    val respuestaReduce: Int= arregloDinamico
        .reduce{ //acumulado=0-> siempre empieza en 0
            acumulado: Int, valorActual:Int->
            return@reduce (acumulado+valorActual) //logica de negocio
        }
    println(respuestaReduce)

}

//void -> Unit
fun imprimirNombre(nombre: String): Unit{
    println("Nombre: ${nombre}")
}

//
fun calcularSueldo(
    sueldo: Double, //Requerido
    tasa: Double = 12.00,// Opcional (Defecto)
    bonoEspecial: Double? = null, //Opcion null->nullable
): Double{
    //Cualquier tipo de dato en algun punto puede ser nulo
    //Int -> Int? (nullable)
    //String-> String? (nullable)
    //Date -> Date? (nullable)


    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        return sueldo * (100/tasa) + bonoEspecial
    }
}

abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno: Int,
        dos: Int
    ){//Bloque de codigo constructor
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class Numeros(
    //constructo primario
    //ejemplo
    //uno: Init, (Parametro(sin modificador de acceso))
    //public var uno: Init, //Propiedad Publica Clase numeros.uno
    //var uno: Init, //Propiedad de la clase por defecto es public
    // public var uno: Init,
    protected val numeroUno : Int,  //Propiedad de la clase protected numeros.numeroUno
    protected val numeroDos: Int,
) {
    init {
        this.numeroUno; this.numeroDos;
        numeroUno; numeroDos;
        println("Inicializando")
    }

    class Suma( //constructor primario suma
        uno: Int,//parametro
        dos Int//parametro
    ) : Numeros(uno, dos) {//<- constructor del padre
    init {//bloque constructor primario
        this.numeroUno; numeroUno;
        this.numeroDos; numeroDos;
    }

    }

    constructor( // segundo constructor
        uno: Int?, //parametros
        dos: Int //parametros
    ) : this( //llamada al constructor primario
        if (uno == null) 0 else uno,
        dos
    ) {
        numeroUno;
    }

    constructor( // segundo constructor
        uno: Int, //parametros
        dos: Int? //parametros
    ) : this( //llamada al constructor primario
        if (dos == null) 0 else dos,
        uno
    ) // si no lo necesitamos al bloque de codigo "{}" lo omitimos

    constructor( // segundo constructor
        uno: Int?, //parametros
        dos: Int? //parametros
    ) : this( //llamada al constructor primario
        if (uno == null) 0 else uno,
        if (dos == null) 0 else dos,
    ) {
        numeroUno;
        numeroDos;
    }

    //si no se ponen los modificadores se pone public por defecto, si no se puede usar private or protected
    public fun sumar(): Int{
        val total= numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }
    companion object{//atributos y metodos compartidos
        //entre las instancias
        val pi = 3.14
        fun elecarAlCuadrado(num: Int): Int{
            return num*num
        }

        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma:Int){
            historialSumas.add(valorNuevaSuma)
        }

    }

}