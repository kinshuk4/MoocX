
def m_update(mu,sigma2,nu,r2):
    sigma2_p = 1.0/((1.0/sigma2)+(1.0/r2))
    mu_p = ((r2*mu)+(sigma2*nu)) / (r2 + sigma2)
    return (mu_p, sigma2_p)


print m_update(1.0,2.0,25.0,2.0)