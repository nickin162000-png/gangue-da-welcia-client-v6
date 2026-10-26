import csv, io, os, re, sys, zipfile
G = os.path.join(os.path.expanduser("~"), ".gradle")
M = {}
def add(t):
    for r in csv.reader(io.StringIO(t)):
        if len(r) >= 2 and r[0].startswith(("func_", "field_")):
            M[r[0]] = r[1]
for d, _, fs in os.walk(G):
    for f in fs:
        p = os.path.join(d, f)
        if f.startswith("mcp_stable") and f.endswith(".zip"):
            z = zipfile.ZipFile(p)
            for n in z.namelist():
                if n.endswith(("fields.csv", "methods.csv")):
                    add(z.read(n).decode("utf-8", "replace"))
if not M:
    print("ERRO: tabela mcp_stable nao encontrada")
    sys.exit(1)
print("Nomes na tabela:", len(M))
pat = re.compile(r"\b(?:func|field)_\d+_[A-Za-z0-9_]+\b")
miss = set()
cnt = [0]
def sub(m):
    n = m.group(0)
    if n in M:
        cnt[0] += 1
        return M[n]
    miss.add(n)
    return n
for d, _, fs in os.walk("src/main/java"):
    for f in fs:
        if f.endswith(".java"):
            p = os.path.join(d, f)
            t = open(p, encoding="utf-8").read()
            u = pat.sub(sub, t)
            if u != t:
                open(p, "w", encoding="utf-8").write(u)
print("Trocas:", cnt[0])
print("Sem nome:", sorted(miss))
