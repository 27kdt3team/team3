from pykis import KisAuth

auth = KisAuth(
            id="kmin92",
            appkey="PSZdQZuIDcogxQBKSAyGh4uI2fjlw1VUNwMY",
            secretkey="paJ83m77TM3wjvDWZVEHaUxs+4bJRqGB698TL4JV/O0C+gKBM3AG8u4ZtWR2rCSivCwTqoFxkO6poj4z8hgEEzUfsIaYurdhXQFHA++pKWej7HKRM64YZ/PQ8MHQS60yk43/eZFXtEVxHJkMoNAg77f/cl3Cqxa7RPCP7ERIg2AJJ3aDGk4=",
            account="73765250-01",
            virtual=False
        )

auth.save("secret.json")