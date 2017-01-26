package org.mk.training.jdbc;
import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.xa.XAResource;

public class DummyResourceAdapter implements ResourceAdapter
{
   public void endpointActivation(MessageEndpointFactory endpointFactory, ActivationSpec spec) throws ResourceException
   {
      throw new UnsupportedOperationException("NOT SUPPORTED FOR 1.0 ADAPTERS");
   }

   public void endpointDeactivation(MessageEndpointFactory endpointFactory, ActivationSpec spec)
   {
      throw new UnsupportedOperationException("NOT SUPPORTED FOR 1.0 ADAPTERS");
   }

   public XAResource[] getXAResources(ActivationSpec[] specs) throws ResourceException
   {
      return new XAResource[0];
   }

   public void start(BootstrapContext ctx) throws ResourceAdapterInternalException
   {
      // TODO start
   }

   public void stop()
   {
      // TODO stop
   }
}
