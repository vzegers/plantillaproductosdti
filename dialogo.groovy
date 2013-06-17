<% 
  import javax.jcr.Node;
	import org.exoplatform.download.DownloadService;
	import org.exoplatform.download.InputStreamDownloadResource;
	import java.io.InputStream;
		
	private void setUploadFields(name) {
		String[] illustrationHiddenField1 = ["jcrPath=/node/medias/images/illustration", "nodetype=nt:file", "mixintype=mix:referenceable", "defaultValues=illustration"];
		String[] illustrationHiddenField2 = ["jcrPath=/node/medias/images/illustration/jcr:content", "nodetype=nt:resource", "mixintype=dc:elementSet", "visible=false"]; 
		String[] illustrationHiddenField3 = ["jcrPath=/node/medias/images/illustration/jcr:content/jcr:encoding", "visible=false", "UTF-8"];  
		String[] illustrationHiddenField4 = ["jcrPath=/node/medias/images/illustration/jcr:content/jcr:lastModified", "visible=false"];
		String[] illustrationHiddenField5 = ["jcrPath=/node/medias/images/illustration/jcr:content/dc:date", "visible=false"];									
		uicomponent.addHiddenField("illustrationHiddenField1", illustrationHiddenField1);
		uicomponent.addHiddenField("illustrationHiddenField2", illustrationHiddenField2);
		uicomponent.addHiddenField("illustrationHiddenField3", illustrationHiddenField3);
		uicomponent.addCalendarField("illustrationHiddenField4", illustrationHiddenField4);
		uicomponent.addCalendarField("illustrationHiddenField5", illustrationHiddenField5);			
   		String[] fieldImage = ["jcrPath=/node/medias/images/illustration/jcr:content/jcr:data"] ;
      	uicomponent.addUploadField(name, fieldImage) ;	
	}
	
	Node ProductNode = uicomponent.getNode();
%>

<div class="UIForm FormLayout FormScrollLayout"> 
<%uiform.begin()%>  
		<div class="WorkingAreaWithHelp">
			<%/* start render action*/%>
      <% if (uiform.isShowActionsOnTop()) {
                uiform.processRenderAction()
             }
      %>

			<%/* end render action*/%> 
	<div class="HorizontalLayout">
		<table class="UIFormGrid">
		   
			<tr>
				<td class="FieldLabel">Nombre Servicio</td>
				<td class="FieldComponent">
				<%
					String[] productFieldTitle = ["jcrPath=/node/exo:title", "validate=empty", "editable=if-null"];
					uicomponent.addTextField("title", productFieldTitle) ;
				%>

				
				</td>
			</tr>

			 <tr>
			<td class="FieldLabel">Nombre Nodo (Automático)</td>
				<td class="FieldComponent">
				<%
				String[] producthiddenField5 = ["jcrPath=/node", "mixintype=mix:votable,mix:commentable","editable=if-null","validate=name,empty"] ;
				uicomponent.addTextField("name", producthiddenField5) ; 
				
				String[] documentsFolder = ["jcrPath=/node/documents", "nodetype=nt:folder","mixintype=exo:documentFolder", "defaultValues=documents"] ;
				String[] mediasFolder = ["jcrPath=/node/medias", "nodetype=exo:multimediaFolder", "defaultValues=medias"] ;
				String[] imagesFolder = ["jcrPath=/node/medias/images", "nodetype=nt:folder", "defaultValues=images"] ;
				String[] videoFolder = ["jcrPath=/node/medias/videos", "nodetype=nt:folder", "defaultValues=videos"] ;
				uicomponent.addHiddenField("documentsFolder", documentsFolder);
				uicomponent.addHiddenField("mediasFolder", mediasFolder);
				uicomponent.addHiddenField("imagesFolder", imagesFolder);
				uicomponent.addHiddenField("videoFolder", videoFolder);
		        %>

		         <script type="text/javascript">
                    titleField = document.getElementById("title");
                    titleField.onchange = function() { eXo.ecm.SELocalization.cleanName(this.value, "name"); } ;
                    </script>
		        </td>
		    </tr>
			<tr>
		      <td class="FieldLabel"><label for="categories">Categoría Servicio</label></td>
		      <td class="FieldComponent">
			      <% 
			        String[] fieldCategories = ["jcrPath=/node/exo:category", "multiValues=true", "reference=true", "editable=false"];
			        uicomponent.addTextField("categories", fieldCategories);

			      %>
			    </td>
			  </tr>
			

			
			<tr>
				<td class="FieldLabel">Imagen Miniatura Servicio</td>
				<td class="FieldComponent">
					<%						
						String illustration = "illustration";
						if(ProductNode != null && ProductNode.hasNode("medias/images/illustration") && (uicomponent.findComponentById(illustration) == null)) {
							def imageNode = ProductNode.getNode("medias/images/illustration") ;
							def resourceNode = imageNode.getNode("jcr:content");
							if(resourceNode.getProperty("jcr:data").getStream().available() > 0) {
								def imgSrc = uicomponent.getImage(imageNode, "jcr:content");
								def actionLink = uicomponent.event("RemoveData", "/medias/images/illustration/jcr:content");
								%>		
									<div>
										<image src="$imgSrc" width="100px" height="80px"/>
										<a onclick="$actionLink">
											<img src="/eXoResources/skin/DefaultSkin/background/Blank.gif" class="ActionIcon Remove16x16Icon" alt="" />
										</a>
									</div>
								<%
							} else {
								setUploadFields(illustration);
							}
						} else {
							setUploadFields(illustration);
						}
					%>
				</td>			
			</tr>
			<tr>
				<td class="FieldLabel">Resumen Portada</td>
				<td class="FieldComponent">
				<%
					String[] fieldSummary = ["jcrPath=/node/exo:summary", "options=Basic", ""] ;
					uicomponent.addRichtextField("summary", fieldSummary) ;
				%>
				</td>
			</tr>
			<tr>
				<td class="FieldLabel">Descripci&oacute;n</td>
				<td class="FieldComponent">
				<div class="UIFCKEditor">
				<%
					String[] productFieldBenefits = ["jcrPath=/node/exo:productBenefits", "options=toolbar:CompleteWCM", ""] ;
					uicomponent.addRichtextField("productBenefits", productFieldBenefits) ;        
				%>
				</div>
				</td>
			</tr>
			<tr>
				<td class="FieldLabel">Contacto</td>
				<td class="FieldComponent">
				<div class="UIFCKEditor">
				<%
					String[] productFieldFeatures = ["jcrPath=/node/exo:productFeatures", "options=toolbar:CompleteWCM", ""] ;
					uicomponent.addRichtextField("productFeatures", productFieldFeatures) ;        
				%>
				</div>
				</td>
			</tr>
		</table>
			     <% if (!uiform.isShowActionsOnTop()) {
                     uiform.processRenderAction()
			            }
			     %>
		</div>
		</div>   <!-- End of WorkingAreaWithHelp -->
		<%uiform.end()%>
</div>
