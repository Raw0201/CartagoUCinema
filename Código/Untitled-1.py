import os
opcion = 0
reservas = []
rows = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J']
cols = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10']

def crear_cine():
    cine = []

    for r, row in enumerate(rows):
        cine.append([])
        for col in cols:
            cine[r].append(f"{row}{col}")

    return cine

cine = crear_cine()

def mostrar_notificacion(notificacion):
    print(notificacion)

def mostrar_cine():
    os.system("cls")
    wall = "."

    print(f"{wall} " * 31)
    print(f"{wall}{' ' * 3}{'-' * 21}PANTALLA{'-' * 21}{' ' * 6}{wall}")
    print(f"{wall}{' ' * 59}{wall}")
    print(f"{wall}{' ' * 54}{wall}{' ' * 4}{wall}")
    for row in cine:
        print(f"{wall}", end="")
        for i, seat in enumerate(row):
            seat = f"|{seat}|  " if i in (1, 7) else f"|{seat}|"
            print(seat, end="")
        print(f"{wall}    {wall}")
    print(f"{wall} " * 31)

def calcular_boletos(lista):
    total = lista[0] + lista[1] + lista[2]
    return(total)

def validar_asientos(row, col, cantidad_boletos):
    if col + cantidad_boletos > len(cols):
        return 0

    for i in range(cantidad_boletos):
        if cine[row][col + i] in ("RES", "PAG"):
            valid = 0
        else:
            valid = 1
    return valid

def reservar_p2(reserva):
    cantidad_boletos = calcular_boletos(reserva)
    asiento = input("Seleccione el asiento")
    fila, columna = asiento[0].upper(), asiento[1:]

    if fila not in rows or columna not in cols:
        mostrar_cine()
        mostrar_notificacion("Asiento invalido")
    else:
        row = rows.index(fila)
        col = cols.index(columna)

        valid = validar_asientos(row, col, cantidad_boletos)
        if valid:
            for i in range(cantidad_boletos):
                cine[row][col + i] = "RES"
            mostrar_cine()
            mostrar_notificacion("Asientos reservados correctamente")
            return
        else:
            mostrar_cine()
            mostrar_notificacion("No hay espacio disponible en ese asiento")
        
    reservar_p2(reserva)

def reservar_p1(reserva):
    print("Reservar")
    mostrar_cine()
    mostrar_notificacion("")
    

    tipo_boleto = 0
    
    while(tipo_boleto != 9):
        tipo_boleto = int(input("Indique el tipo de boleto: "))

        if tipo_boleto in (1, 2, 3):
            compra = int(input("Indique la cantidad de boletos: "))

            if reserva[tipo_boleto - 1] + compra >= 0:
                reserva[tipo_boleto - 1] += compra
                print(reserva)
            else:
                mostrar_cine()
                mostrar_notificacion("La cantidad de boletos no puede ser menor que 0")
        elif tipo_boleto == 9 and reserva != [0, 0, 0]:
            reservas.append(reserva)
            mostrar_cine()
            mostrar_notificacion("")
            reservar_p2(reserva)
        elif tipo_boleto == 9 and reserva == [0, 0, 0]:
            print("Reserva cancelada")
        else:
            mostrar_cine()
            mostrar_notificacion("Opcion no valida")

def pagar():
    print("Pagar")

def cancelar():
    print("Cancelar")

def estadisticas():
    print("Estadisticas")

while opcion != 5:
    print(
    """
    ------------
    Sala de cine
    ------------

    Seleccione una opcion:

    (1) Reservar boletos
    (2) Pagar boletos
    (3) Cancelar reservas
    (4) Ver estadisticas del cine
    (5) Salir del programa
    """)

    opcion = int(input("Opcion: "))
    os.system("cls")

    match opcion:
        case 1:
            reservar_p1([0, 0, 0])
        case 2:
            pagar()
        case 3:
            cancelar()
        case 4:
            estadisticas()
        case 5:
            print("Salir")
        case _:
            print("Error")




# boletos = 0

# while(True):

#     tipo_boleto = input("Indique el tipo de boleto: ")

#     if tipo_boleto == "1":
#         print("niño")
#     elif tipo_boleto == "2":
#         print("joven")
#     elif tipo_boleto == "3":
#         print("anciano")
#     else:
#         print("no válido")

#     compra = int(input("Indique la cantidad de boletos: "))

#     boletos+=compra

#     print(boletos)