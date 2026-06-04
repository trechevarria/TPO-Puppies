# TPO-Puppies (UADE)

Trabajo práctico de Proceso de Desarrollo de Software: sistema de refugio animal (adopciones, visitas, alarmas veterinarias).

## Estructura

- `src/domain` — reglas de negocio y patrones (Strategy, Factory, Adapter)
- `src/application` — controllers, services, DTOs, repositories (interfaces)
- `src/infrastructure` — repositorios in-memory y mock de seguridad
- `test` — pruebas manuales (`TestAdopcion.java`)

## Compilar y ejecutar tests

```powershell
cd TPO-Puppies
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse -Filter *.java src,test | ForEach-Object { $_.FullName })
java -cp out TestAdopcion
```

## Colaborar en Git

```bash
git clone https://github.com/trechevarria/TPO-Puppies.git
git checkout -b feature/mi-cambio
# ... editar ...
git add .
git commit -m "Descripcion del cambio"
git push origin feature/mi-cambio
```

Abrir un Pull Request en GitHub para revisar antes de mergear a `main`.
