import random

products = ['A', "B", "C", 'D', 'E']
transantionID = 1
# for transantionID in range(1, 5):
#     trans = random.sample(products,3)
#     print(transantionID ,trans)

with open('/Users/ziyi/document/CS1699/hw4/input.txt', 'w') as f:
    for transantionID in range(1, 101):
        trans = random.sample(products,3)
        # print('{}\t{}\t{}\t{}\n'.format(transantionID, trans[0], trans[1], trans[2]))
        f.write('{}, {} {} {}\n'.format(transantionID, trans[0], trans[1], trans[2]))
