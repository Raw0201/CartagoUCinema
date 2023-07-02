rows = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J']
cols = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10']
seats = []

seat = "A05"

row = rows.index(seat[0])
col = cols.index(seat[1:])
pos = f"{row},{col}"

for r, row in enumerate(rows):
    seats.append([])
    for c, col in enumerate(cols):
        seats[r].append(f"{row}{col}")

seats[0][0] = "RES"
seats[0][1] = "PAG"

wall = "."

print(f"{wall} " * 31)
print(f"{wall}{' ' * 3}{'P' * 49}{' ' * 2}     {wall}")
print(f"{wall}{' ' * 59}{wall}")
print(f"{wall}{' ' * 59}{wall}")
for row in seats:
    print(f"{wall}", end="")
    for i, seat in enumerate(row):
        seat = f"|{seat}|  " if i in (1, 7) else f"|{seat}|"
        print(seat, end="")
    print(f"{wall}    {wall}")
print(f"{wall} " * 31)